<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <meta charset="UTF-8">
<head>
    <title>당신만의 타로 카드를 만들어보세요!</title>
    <link href="<%= request.getContextPath() %>/assets/style.css" rel="stylesheet" type="text/css">
    <meta property="og:title" content="<%= request.getAttribute("ogTitle") %>" />
    <meta property="og:description" content="<%= request.getAttribute("ogDescription") %>" />
    <meta property="og:image" content="<%= request.getAttribute("ogImageUrl") %>" />
    <meta property="og:url" content="<%= request.getAttribute("ogPageUrl") %>" />
</head>
<body>
    <main>
        <section>
            <h1>🃏나만의 타로카드 만들기🃏</h1>
        </section>
        <section>
            <figure>
                <img alt="main-image" src="<%= request.getContextPath() %>/img/taro.jpeg">
            </figure>
            <form method="post">
                <label for="description">
                    ⌨️ 설명 입력
                </label>
                <input id="description" type="text" name="description" placeholder="당신의 오늘 기분을 적어주세요!(ex 행복함, 우울함)" required>
                <button>결과 보기</button>
            </form>
        </section>
    </main>
</body>
</html>
