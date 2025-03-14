# today-taro-card

## 📌 프로젝트 소개
타로 카드 생성 프로젝트는 사용자의 기분에 따라 타로 카드를 뽑고, 해당 카드의 의미를 확인할 수 있도록 구현된 웹 애플리케이션입니다. 
이 프로젝트는 JSP와 서블릿을 사용하여 gemini-2.0-flash 모델을 기반으로 타로카드 프롬프팅을 수행하고, black-forest-labs/FLUX.1-schnell-Free 모델에서 타로카드 이미지를 생성한 후 Supabase에 저장하는 기능을 제공합니다.

## 🛠 기술 스택
- **Frontend**: JSP, CSS
- **Backend**: Servlet, Supabase

## 🎯 주요 기능
1. 타로카드 프롬프팅
- Gemini 모델을 활용하여 사용자 입력을 기반으로 타로카드 프롬프트 생성
2. 타로카드 이미지 생성
- Together API를 사용하여 프롬프트 기반의 타로카드 이미지 생성
3. 프롬프트, 이미지 저장
생성된 이미지를 Supabase에 저장하여 관리

## 🚀 실행 방법
1. 프로젝트를 다운로드하거나 클론합니다.
```sh
git clone https://github.com/soheeGit/today-taro-card.git
```
2. 프로젝트 폴더로 이동합니다.
```sh
cd today-taro-card
```
3. .env 파일 생성
```env
GEMINI_KEY=본인 키
SUPABASE_URL=본인 url
SUPABASE_KEY=본인 키
TOGETHER_KEY=본인 키
```
4. 브라우저에서 실행합니다.

## 📌 향후 개선 사항
- 프론트엔드 디자인 개선

## 실행예시

<img width="439" alt="스크린샷 2025-03-14 오전 10 28 19" src="https://github.com/user-attachments/assets/d0873133-3417-470b-bf83-2e9f466dad4a" />

<img width="599" alt="스크린샷 2025-03-14 오전 10 12 13" src="https://github.com/user-attachments/assets/1c7fad8f-8256-426d-acfe-2819e0a4f579" />



