package com.vehicle.utils;

import com.vehicle.base.constants.ApproveTypeEnum;
import com.vehicle.base.constants.Constants;
import com.vehicle.base.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 申请工具
 *
 * @author lijianbing
 * @date 2023/9/1 23:20
 */
@Slf4j
public class ApplyUtil {

    private static Lock lock = new ReentrantLock();

    public static String getNo(ApproveTypeEnum typeEnum) {
        StringBuilder no = new StringBuilder();
        String prefix = "";
        switch (typeEnum) {
            case VEHICLE:
                prefix = Constants.APPLY_VEHICLE_NO_PREFIX;
                break;
            case EXPENSE:
                prefix = Constants.APPLY_EXPENSE_NO_PREFIX;
                break;
            case PERSON:
                prefix = Constants.APPLY_PERSON_NO_PREFIX;
                break;
            default:
                throw BizException.error("申请类型不存在");
        }
        no.append(prefix).append(getPkByTimeAndUuid14());
        return no.toString();
    }

    /**
     * 随机产生32位16进制字符串
     *
     * @return 字符串
     */
    public static String getRandom32Pk() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 描述：14位主键生成，前8位：YYMMDD  后6位：32位UUID里随机6位
     * 主键有序到分钟级别，分钟内主键无序
     *
     * @return 字符串
     */
    public static String getPkByTimeAndUuid14() {
        lock.lock();
        StringBuilder pk = new StringBuilder();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate now = LocalDate.now();
            String today = now.format(formatter);
            pk.append(today);

            String random32 = getRandom32Pk();
            int x = 0;
            int capacity = 6;
            for (int j = 0; j < capacity; j++) {
                x = new Random().nextInt(32);
                if (x == 0) {
                    x = 1;
                }
                pk.append(random32.substring(x - 1, x));
            }
            return pk.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            lock.unlock();
        }
        return null;
    }
}
