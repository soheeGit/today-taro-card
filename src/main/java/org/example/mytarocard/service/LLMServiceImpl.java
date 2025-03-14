package org.example.mytarocard.service;

import org.example.mytarocard.model.dto.LLMServiceParam;
import org.example.mytarocard.model.dto.LLMServiceResponse;
import org.example.mytarocard.model.repository.LLMRepository;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.logging.Logger;

public class LLMServiceImpl implements LLMService{
    private LLMServiceImpl() {}
    public static LLMService instance = new LLMServiceImpl();
    public static LLMService getInstance() {
        return instance;
    }

    private final Logger logger = Logger.getLogger(LLMServiceImpl.class.getName());

    private final LLMRepository llmRepository = LLMRepository.getInstance();

    @Override
    public LLMServiceResponse callAPI(LLMServiceParam param) throws IOException, InterruptedException {
        logger.info("callAPI");
        String prompt = """
                %s의 기분을 바탕으로 오늘을 극복할 수 있는 특이한 타로 카드를 만들기 위한 설명을 작성해줘. 마크다운 없는 평문으로. 사족을 붙이지 말고 500자 이하의 내용만 출력해줘. 결과는 일반적인 한국어 문장으로 html 형식으로 작성되어야 하는데 아래 예시의 틀을 정확하게 따라주는데, 앞에 html 이런 제목을 붙이지 말고 줄바꿈도 하지 마.
                예시: <div class="carddescription"><p><strong>카드 이름:</strong> 침묵의 정원</p><p><strong>상징:</strong> 앙상한 가지의 흑백 나무가 짙은 안개 속에 잠겨 있다. 나무 아래에는 희미하게 빛나는 작은 꽃 한 송이가 고개를 숙이고 있다. 하늘은 먹구름으로 뒤덮여 있지만, 꽃잎에 맺힌 이슬만이 희망의 흔적을 보여준다.</p><p><strong>해석:</strong> 현재 당신은 깊은 우울감과 무력감에 갇혀 있다. 모든 것이 멈춰버린 듯한 침묵 속에서 고립감을 느낄 수 있다. 그러나 이 카드는 절망 속에서도 작은 희망이 존재함을 상기시킨다. 비록 눈에 띄지 않더라도, 당신 안에는 다시 피어날 가능성이 잠재되어 있다.</p><p><strong>오늘의 극복:</strong> 자신을 다그치거나 무리하게 긍정적인 감정을 강요하지 마라. 대신, 작은 것에서부터 시작하라. 따뜻한 차 한 잔을 마시거나, 짧은 산책을 하는 것처럼 아주 작은 행동 하나하나가 침묵의 정원에 물을 주는 것과 같다. 스스로에게 친절하고, 작은 빛을 발견하려 노력하라. 지금은 침묵 속에서 자신을 돌보는 시간이 필요하다.</p></div> 이런식의 틀로 출력해주면 돼
                """.formatted(param.prompt());

        return new LLMServiceResponse(llmRepository.callModel(param.model(), prompt));
    }

    @Override
    public LLMServiceResponse callAPITogether(LLMServiceParam param) throws IOException, InterruptedException {
        logger.info("callAPITogether");
        String prompt = """
                %s 이 글에서 상징에 나오는 단어들을 그대로 타로 카드 이미지 뽑아줘. 분위기는 몽롱하게
                """.formatted(param.prompt());

        return new LLMServiceResponse(llmRepository.callModel(param.model(), prompt));
    }
}
