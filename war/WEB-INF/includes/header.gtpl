        <% if (user && users.isUserAdmin()) { %>
        <div style="float:right"><a href="${users.createLogoutURL('/')}">logout</a></div>
        <% } %>
        <div id="header">
        <h1><a href="/" style="color:#D33381">Gist Feed</a></h1>
        </div>
        <div>
