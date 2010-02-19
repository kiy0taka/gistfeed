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
<ul class="index">
<li><a href="/extensions/index/a">&nbsp;a&nbsp;</a>
<li><a href="/extensions/index/b">&nbsp;b&nbsp;</a>
<li><a href="/extensions/index/c">&nbsp;c&nbsp;</a>
<li><a href="/extensions/index/d">&nbsp;d&nbsp;</a>
<li><a href="/extensions/index/e">&nbsp;e&nbsp;</a>
<li><a href="/extensions/index/f">&nbsp;f&nbsp;</a>
<li><a href="/extensions/index/g">&nbsp;g&nbsp;</a>
<li><a href="/extensions/index/h">&nbsp;h&nbsp;</a>
<li><a href="/extensions/index/i">&nbsp;i&nbsp;</a>
<li><a href="/extensions/index/j">&nbsp;j&nbsp;</a>
<li><a href="/extensions/index/k">&nbsp;k&nbsp;</a>
<li><a href="/extensions/index/l">&nbsp;l&nbsp;</a>
<li><a href="/extensions/index/m">&nbsp;m&nbsp;</a>
<li><a href="/extensions/index/n">&nbsp;n&nbsp;</a>
<li><a href="/extensions/index/o">&nbsp;o&nbsp;</a>
<li><a href="/extensions/index/p">&nbsp;p&nbsp;</a>
<li><a href="/extensions/index/q">&nbsp;q&nbsp;</a>
<li><a href="/extensions/index/r">&nbsp;r&nbsp;</a>
<li><a href="/extensions/index/s">&nbsp;s&nbsp;</a>
<li><a href="/extensions/index/t">&nbsp;t&nbsp;</a>
<li><a href="/extensions/index/u">&nbsp;u&nbsp;</a>
<li><a href="/extensions/index/v">&nbsp;v&nbsp;</a>
<li><a href="/extensions/index/w">&nbsp;w&nbsp;</a>
<li><a href="/extensions/index/x">&nbsp;x&nbsp;</a>
<li><a href="/extensions/index/y">&nbsp;y&nbsp;</a>
<li><a href="/extensions/index/z">&nbsp;z&nbsp;</a>
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
