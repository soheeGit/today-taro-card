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
        String url = "";  // 이미지 URL을 저장할 변수
        try {
            String[] data = new String[]{supabaseService.findById(uuid)}; // 저장된 데이터 가져오기
            req.setAttribute("data", data);
            url = data[1]; // togetherResponse.data()에 해당하는 이미지 URL
            req.setAttribute("result", url); // JSP에서 사용할 수 있도록 설정
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        view(req, resp, "result");
    }
}
