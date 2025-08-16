package com.mimu_bird.common.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * 시간 관련 유틸리티 함수
 */
object TimeUtil {
    
    /**
     * ISO 8601 형식의 문자열을 LocalDateTime으로 파싱
     */
    fun parseIsoDateTime(dateTimeString: String): LocalDateTime? {
        return try {
            LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME)
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * 다음 업데이트 시간까지 남은 시간을 계산하여 HH:MM:SS 형식으로 반환
     */
    fun calculateTimeUntilNextUpdate(nextFetchTime: String): Triple<Int, Int, Int> {
        val nextUpdate = parseIsoDateTime(nextFetchTime) ?: return Triple(24, 0, 0)
        val now = LocalDateTime.now()
        
        // 다음 업데이트 시간이 현재 시간보다 이전이면 24시간 후로 설정
        if (nextUpdate.isBefore(now)) {
            return Triple(24, 0, 0)
        }
        
        val hours = ChronoUnit.HOURS.between(now, nextUpdate).toInt()
        val minutes = ChronoUnit.MINUTES.between(now, nextUpdate).toInt() % 60
        val seconds = ChronoUnit.SECONDS.between(now, nextUpdate).toInt() % 60
        
        return Triple(hours, minutes, seconds)
    }
} 