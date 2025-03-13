package org.example.mytarocard.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mytarocard.service.SupabaseService;
import org.example.mytarocard.service.SupabaseServiceImpl;

import java.io.IOException;

@WebServlet("/result/*")
public class TaroResultController extends Controller{
    SupabaseService supabaseService;
    @Override
    public void init() throws ServletException {
        log("TaroResultController init");
        supabaseService = SupabaseServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("TaroResultController doGet");
        String uuid = req.getPathInfo().substring(1);  // URL에서 UUID 추출
        try {
            String[] dataFromSupabase = supabaseService.findById(uuid);

            // dataFromSupabase[0]은 LLM 텍스트 응답 (llmServiceResponse.data())
            req.setAttribute("data", dataFromSupabase[0]);

            // dataFromSupabase[1]은 이미지 URL (togetherResponse.data())
            req.setAttribute("result", dataFromSupabase[1]);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        view(req, resp, "result");
    }
}
