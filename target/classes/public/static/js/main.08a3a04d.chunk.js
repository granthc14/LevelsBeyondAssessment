(window.webpackJsonp=window.webpackJsonp||[]).push([[0],[,,,,,,,,function(e,t,a){e.exports=a(18)},,,,,,function(e,t,a){},function(e,t,a){},function(e,t,a){e.exports=a.p+"static/media/logo.5d5d9eef.svg"},function(e,t,a){},function(e,t,a){"use strict";a.r(t);var n=a(0),l=a.n(n),s=a(7),o=a.n(s),c=(a(14),a(1)),r=a(2),i=a(4),u=a(3),m=a(5),d=(a(15),function(e){function t(){var e;return Object(c.a)(this,t),(e=Object(i.a)(this,Object(u.a)(t).call(this))).state={issues:[]},e}return Object(m.a)(t,e),Object(r.a)(t,[{key:"componentDidMount",value:function(){var e=this,t=new Date;t.setDate(t.getDate()-7);var a=t.toISOString();fetch("https://api.github.com/repos/angular/angular/issues?since="+encodeURI(a)).then(function(e){return e.json()}).then(function(t){console.log(t);for(var a=[],n=0;n<t.length;n++){var s=t[n];null!=s.assignee?a.push(l.a.createElement("tbody",null,l.a.createElement("tr",{className:"dataRow",key:n},l.a.createElement("td",{className:"title"},s.title),l.a.createElement("td",{className:"asigneeLogin"},s.assignee.login),l.a.createElement("td",{className:"userLogin"},s.user.login)),l.a.createElement("tr",{className:"bodyRow"},l.a.createElement("td",{className:"body"},s.body)))):a.push(l.a.createElement("tbody",null,l.a.createElement("tr",{className:"dataRow",key:n},l.a.createElement("td",{className:"title"},s.title),l.a.createElement("td",{className:"asigneeLogin"},l.a.createElement("div",{className:"emptyDiv"})),l.a.createElement("td",{className:"userLogin"},s.user.login)),l.a.createElement("tr",{className:"bodyRow"},l.a.createElement("td",{className:"body"},s.body))))}console.log(a),e.setState({issues:a})})}},{key:"render",value:function(){return l.a.createElement("div",null,l.a.createElement("div",null,"Below is a table containing a title, assignee login, user login, and body for each issue."),l.a.createElement("table",{className:"issuesTable"},l.a.createElement("tbody",null,l.a.createElement("tr",null,l.a.createElement("td",null," Title "),l.a.createElement("td",null," Assignee Login "),l.a.createElement("td",null," User Login "))),this.state.issues))}}]),t}(n.Component)),g=(a(16),a(17),function(e){function t(){return Object(c.a)(this,t),Object(i.a)(this,Object(u.a)(t).apply(this,arguments))}return Object(m.a)(t,e),Object(r.a)(t,[{key:"render",value:function(){return l.a.createElement("div",null,l.a.createElement(d,null))}}]),t}(n.Component));Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));o.a.render(l.a.createElement(g,null),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then(function(e){e.unregister()})}],[[8,1,2]]]);
//# sourceMappingURL=main.08a3a04d.chunk.js.map