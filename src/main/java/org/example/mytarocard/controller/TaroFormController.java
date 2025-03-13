package org.example.mytarocard.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mytarocard.model.constant.LLMModel;
import org.example.mytarocard.model.dto.LLMServiceParam;
import org.example.mytarocard.model.dto.LLMServiceResponse;
import org.example.mytarocard.service.LLMService;
import org.example.mytarocard.service.LLMServiceImpl;
import org.example.mytarocard.service.SupabaseService;
import org.example.mytarocard.service.SupabaseServiceImpl;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/")
public class TaroFormController extends Controller{
    private LLMService llmservice;
    private SupabaseService supabaseService;

    @Override
    public void init() throws ServletException {
        log("TaroFormController init");
        llmservice = LLMServiceImpl.getInstance();
        supabaseService = SupabaseServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("TaroFormController doGet");
        req.setAttribute("pageTitle", "당신의 타로를 만들어보세요!");
        req.setAttribute("ogTitle", "당신의 타로를 만들어보세요!");
        req.setAttribute("ogDescription", "개인적인 경험과 감정을 바탕으로 자신만의 타로 카드를 생성하고, 그 카드가 전달하는 메시지를 통해 자아를 탐구할 수 있는 웹 애플리케이션. 창의적이고 직관적인 방식으로 타로 카드를 만들고, 삶의 방향성을 찾을 수 있는 재미있는 경험을 제공합니다.");
        req.setAttribute("ogImageUrl", "%s/img/%s".formatted(req.getContextPath(), "taro.jpeg"));
        req.setAttribute("ogPageUrl", req.getContextPath());
        view(req, resp, "form");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("TaroFormController doPost");
        String description = req.getParameter("description");
        LLMServiceParam param = new LLMServiceParam(LLMModel.GEMINI_2_0_FLASH, description);
        LLMServiceResponse llmServiceResponse = null;
        String uuid = UUID.randomUUID().toString();

        try {
            llmServiceResponse = llmservice.callAPI(param);
//            System.out.println(llmServiceResponse);
//            System.out.println(llmServiceResponse.data());
            log(llmServiceResponse.data());
            supabaseService.save(uuid, llmServiceResponse.data(), "");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/result/%s".formatted(uuid));
    }
}
