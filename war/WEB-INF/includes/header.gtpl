        <% if (user && users.isUserAdmin()) { %>
        <div style="float:right"><a href="${users.createLogoutURL('/')}">logout</a></div>
        <% } %>
        <div id="header">
        <h1>
          <a href="/" style="color:#D33381">Gist Feed</a>
          <form style="float:right; padding:20px 10px 0px 0px; margin:0px" action="/gist/list" method="get">
            <input name="q" type="text" size="30" placeholder="File name search"/>
          </form>
        </h1>
        </div>
        <div style="clear: both;">
