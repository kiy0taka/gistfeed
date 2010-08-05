<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-type" content="text/html;charset=UTF-8">
<title>Gist Feed</title>
<link rel="stylesheet" type="text/css" href="/css/main.css">
<link rel="shortcut icon" href="/images/gaelyk-small-favicon.png" type="image/png">
<link rel="icon" href="/images/gaelyk-small-favicon.png" type="image/png">
<% request.extensions.each { extension -> %>
<link rel="alternate" type="application/atom+xml" href="/gist/${extension.key.name}/atom" title="Gist Feed - ${extension.key.name}">
<% } %>
<style type="text/css">
<!--
body {
    padding: 0px 10px 0px 10px;
}
ul.index {
    list-style-type: none;
    font-size: 19px;
    text-align: center;
    float: right;
    padding-right: 20px;
}
ul.index li {
    float: left;
    width: 22px;
    font-weight: bold;
}
div.gist-data {
    max-height: 400px;
    overflow: scroll;
}
-->
</style>
</head>
<% include '/WEB-INF/includes/header.gtpl' %>
<ul class="index"><% ('a'..'z').each { %>
<li><a href="/extensions/index/${it}">&nbsp;${it}&nbsp;</a><% } %>
</ul>
<ul style="margin-left:30px;" class="autopagerize_page_element">
<% request.extensions.each { extension -> %>
<li><a style="font-size:20px" href="/gist/${extension.key.name}/list">${extension.key.name}</a><a href="/gist/${extension.key.name}/atom"><img style="border: 0px; margin-left:5px" src="/images/atom.png" alt="Gist Feed - ${extension.key.name}"></a>
    <% if (user && users?.isUserAdmin()) { %>
      <a href="/admin/delete.groovy?extension=${extension.key.name}${params.page?'&page='+params.page:''}">
        <img style="border: 0px;" src="/images/delete.png" alt="Delete">
      </a>
    <% } %>
<% } %>
</ul>

<div style="margin: 15px 10px 10px 20px" class="autopagerize_insert_before">
<% if (request.hasPrev) { %>
<a href="/extensions${params.index?"/index/"+params.index:""}?page=${request.page-1}">« Prev</a>
<% } else { %>
<span style="color:silver">« Prev</span>
<% } %>
<% if (request.hasNext) { %>
<a href="/extensions${params.index?"/index/"+params.index:""}?page=${(request.page?:1)+1}" rel="next">Next »</a>
<% } else { %>
<span style="color:silver">Next »</span>
<% } %>
</div>
<% include '/WEB-INF/includes/footer.gtpl' %>
</body>
</html>
