package com.gntour.gangneungyeojido.common;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@Slf4j
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("Authentication Filter 생성");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String uri = request.getRequestURI();

        // /admin/login URL을 무시
        if (uri.startsWith("/admin/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // /admin/* 경로를 처리
        if (uri.startsWith("/admin/")) {
            MemberRole role = MemberUtils.getMemberRoleFromSession(session);

            // MEMBER_ROLE이 admin이 아닌 경우
            if (role == null || !role.equals(MemberRole.ADMIN)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/common/unauthorized");
                dispatcher.forward(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("Authentication Filter 파괴");
    }
}
