package com.cctv.heygongc.data.local

class Common {

    companion object {

        var navigationBarHeight = 0     // 안드 네비게이션바 높이 계산해서 빼주기위함
        var fcmToken = ""   // fcm token
        var loginToken = "" // login token. 구글쪽에서 받아온 토큰. 원래 구글쪽도 access token인데 우리 토큰이랑 헷갈리니까 loginToken으로 명시
        var accessToken = ""    //access token
        var refreshToken = ""
        var deviceId = ""   // uuid

    }
}