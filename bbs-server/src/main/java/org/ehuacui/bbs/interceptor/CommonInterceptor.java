package org.ehuacui.bbs.interceptor;

import org.ehuacui.bbs.dto.Constants;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.INotificationService;
import org.ehuacui.bbs.service.IUserService;
import org.ehuacui.bbs.utils.StringUtil;
import org.ehuacui.bbs.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class CommonInterceptor implements Interceptor {

    @Value("${siteTitle}")
    private String siteTitle;
    @Value("${beianName}")
    private String beianName;
    @Value("${tongjiJs}")
    private String tongjiJs;
    @Value("${cookie.domain}")
    private String cookieDomain;
    @Value("${static.domain}")
    private String staticDomain;
    @Value("${solr.status}")
    private String solrStatus;

    @Autowired
    private IUserService userService;
    @Autowired
    private INotificationService notificationService;

    public void invoke(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String user_cookie = WebUtil.getCookie(request, Constants.USER_ACCESS_TOKEN);
        if (StringUtil.notBlank(user_cookie)) {
            String user_access_token = StringUtil.getDecryptToken(user_cookie);
            User user = userService.findByAccessToken(user_access_token);
            if (user == null) {
                WebUtil.removeCookie(response, Constants.USER_ACCESS_TOKEN, "/", cookieDomain);
            } else {
                int count = notificationService.findNotReadCount(user.getNickname());
                request.setAttribute("notifications", count == 0 ? null : count);
                request.setAttribute("userInfo", user);
            }
        }
        request.setAttribute("solrStatus", solrStatus.equalsIgnoreCase("true") ? "true" : "false");
        request.setAttribute("siteTitle", siteTitle);
        request.setAttribute("staticDomain", staticDomain);
        request.setAttribute("beianName", beianName);
        request.setAttribute("tongjiJs", tongjiJs);
    }
}
