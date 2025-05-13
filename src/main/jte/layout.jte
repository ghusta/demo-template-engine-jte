@import org.example.model.Page
@import gg.jte.Content
@param Page page
@param Content content
@param Content contentScriptHead = null
@param Content contentScriptEnd = null
<%-- See: https://jte.gg/syntax/#content --%>
<!DOCTYPE html>
<html lang="en">
<head>
    @if(page.getDescription() != null)
        <meta name="description" content="${page.getDescription()}">
    @endif
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JTE Demo - ${page.getTitle()}</title>
    <link rel="stylesheet" href="./webjars/picocss__pico/css/pico.min.css">
    @if (contentScriptHead != null)
        ${contentScriptHead}
    @endif
</head>
<body>
<header>
</header>
<main class="container">
    <h1>${page.getTitle()}</h1>
    ${content}
</main>
<footer>
</footer>
@if (contentScriptEnd != null)
${contentScriptEnd}
@endif
</body>
</html>