package com.nhinhnguyenuit.jetpackproject.utils

object Helper {
    fun Int.roundNumber(): Int{
        var num = this
        var result = 1;
        while (num > result){
            result*=10;
        }
        return result/10
    }
}