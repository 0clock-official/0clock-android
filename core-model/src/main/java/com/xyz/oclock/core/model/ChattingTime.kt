package com.xyz.oclock.core.model

enum class ChattingTime(val index: Int, val desc: String) {
    PM_10(1, "PM 10:00 ㅤ- ㅤPM 12:00"),
    PM_10_30(2, "PM 10:30 ㅤ- ㅤAM 00:30"),
    PM_11(3, "PM 11:00 ㅤ- ㅤAM 01:00"),
    PM_11_30(4, "PM 11:30 ㅤ- ㅤAM 01:30"),
    AM_12(5, "AM 00:00 ㅤ- ㅤAM 02:00"),
    AM_12_30(6, "AM 00:30 ㅤ- ㅤAM 02:30"),
    AM_11(7, "AM 01:00 ㅤ- ㅤAM 03:00")
}