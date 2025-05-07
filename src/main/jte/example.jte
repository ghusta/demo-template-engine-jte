@import org.example.Page
@param Page page

@template.layout(
page = page,
content = @`<p>Welcome to my example page!</p>`,
contentScriptHead = @`
    <script>
        // page specific scripts to load in head section...
    </script>`,
contentScriptEnd = @`
<script>
    console.log("Page loaded");
</script>`)

