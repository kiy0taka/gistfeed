<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-type" content="text/html;charset=UTF-8">
<title>Gist Feed</title>
<link rel="stylesheet" type="text/css" href="/css/main.css">
<link rel="shortcut icon" href="/images/gaelyk-small-favicon.png" type="image/png">
<link rel="icon" href="/images/gaelyk-small-favicon.png" type="image/png">
<% if (params.extension) { %>
<link rel="alternate" type="application/atom+xml" href="/gist/${params.extension}/atom" title="Gist Feed - ${params.extension}">
<% } %>
</head>
<body>
<% include '/WEB-INF/includes/header.gtpl' %>

<div style="width: 600px; margin-left: 20px; border:3px solid silver" class="autopagerize_page_element">
<% request.gists.each { gist -> %>
<div style='border-top: 1px solid silver; margin-top:-3px;'>
<div style='font-size:14px; padding:5px 5px 10px 5px'>
<a target="_blank" href="http://gist.github.com/${gist.gistNo}">${gist.gistNo}</a>
<span style="color:silver; font-size:12px;">${gist.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</span>
<div style="float: right">
<% gist.extensions.each { ext -> %><a href="/gist/${ext}/list" style="margin-left:5px">${ext}</a><% } %>
</div>
<div style="margin:3px 3px 3px 10px">
<span><a target="_blank" href="http://gist.github.com/${gist.author}">${gist.author}</a></span>
<span>${gist.files.join(", ")}</span>
</div>
</div>
</div>
<% } %>
</div>
<div style="margin:10px" class="autopagerize_insert_before">
<% if (request.hasPrev) { %>
<a href="/gist${params.extension?"/"+params.extension:""}/list?prev=${request.gists[0].gistNo}">« Prev</a>
<% } else { %>
<span style="color:silver">« Prev</span>
<% } %>
<% if (request.hasNext) { %>
<a href="/gist${params.extension?"/"+params.extension:""}/list?next=${request.gists[-1].gistNo}" rel="next">Next »</a>
<% } else { %>
<span style="color:silver">Next »</span>
<% } %>
</div>
<% include '/WEB-INF/includes/footer.gtpl' %>
</body>
</html>
