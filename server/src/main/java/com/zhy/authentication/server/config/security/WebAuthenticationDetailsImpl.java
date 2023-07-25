package com.zhy.authentication.server.config.security;

import com.goudong.core.util.StringUtil;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * 类描述：
 * 自定义web身份验证详细信息(用于登录验证中增加额外参数)
 *
 * @see AuthenticationDetailsSourceImpl
 * @see Authentication#getDetails() 能获取到这里配置的额外信息
 * @Author msi
 * @Date 2021-05-02 10:41
 * @Version 1.0
 */
@Getter
public class WebAuthenticationDetailsImpl extends WebAuthenticationDetails implements Serializable {

    Long selectAppId;

    WebAuthenticationDetailsImpl(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
        String appIdStr = httpServletRequest.getParameter("appId");
        selectAppId = StringUtil.isNotBlank(appIdStr) ? Long.parseLong(appIdStr) : null;
    }

}
