@import org.example.model.Page
@param Page page

@template.layout(
page = page,
content = @`
    <p>Welcome to my example page!</p>
    <div>
        <a href="/">Back to home</a>
    </div>
`,
contentScriptHead = @`
    <script>
        // page specific scripts to load in head section...
    </script>`,
contentScriptEnd = @`
<script>
    console.log("Page loaded");
</script>`)
