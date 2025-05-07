@import org.example.Page
@import gg.jte.Content
@param Page page
@param Content content
@param Content contentScriptHead
@param Content contentScriptEnd
<!DOCTYPE html>
<html lang="en">
<head>
    @if(page.getDescription() != null)
        <meta name="description" content="${page.getDescription()}">
    @endif
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${page.getTitle()}</title>
    ${contentScriptHead}
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
${contentScriptEnd}
</body>
</html>