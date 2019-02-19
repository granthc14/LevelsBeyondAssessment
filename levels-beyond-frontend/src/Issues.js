import React, { Component } from 'react';
import './issues.css';

class Issues extends Component {

  constructor() {
    super();
    this.state = {
      issues: []
    };
  }

  componentDidMount() {
    //get time exactly 1 week before now
    var dateOneWeekAgo = new Date();
    dateOneWeekAgo.setDate(dateOneWeekAgo.getDate() - 7);
    var dateAsISOString = dateOneWeekAgo.toISOString();

    fetch("https://api.github.com/repos/angular/angular/issues?since=" + encodeURI(dateAsISOString))
      .then(results => {
        return results.json();
      }).then( data => {
        console.log(data)
        var issues = []
        for (var i = 0; i < data.length; i++) {
          var dataItem = data[i]
          if (dataItem.assignee != null) {
            issues.push(
              <tbody>
              <tr className="dataRow" key={i}>
                <td className="title">
                  {dataItem.title}
                </td>
                <td className="asigneeLogin">
                  {dataItem.assignee.login}
                </td>
                <td className="userLogin">
                  {dataItem.user.login}
                </td>
              </tr>
              <tr className="bodyRow">
                  <td className="body">
                    {dataItem.body}
                  </td>
              </tr>
              </tbody>
            )
          } else {
            issues.push(

              <tbody>
              <tr className="dataRow" key={i}>
                <td className="title">
                  {dataItem.title}
                </td>
                <td className="asigneeLogin">
                  <div className="emptyDiv"/>
                </td>
                <td className="userLogin">
                  {dataItem.user.login}
                </td>
              </tr>

              <tr className="bodyRow">
                <td className="body">
                  {dataItem.body}
                </td>
              </tr>
              </tbody>
            )
          }
        }

        console.log(issues)
        this.setState({issues: issues})
      });
  }

  render() {
    return (
      <div>
      <div>
        Below is a table containing a title, assignee login, user login, and
        body for each issue.
      </div>


      <table className="issuesTable">
        <tbody>
          <tr>
            <td> Title </td>
            <td> Assignee Login </td>
            <td> User Login </td>
          </tr>
        </tbody>

          {this.state.issues}
      </table>
      </div>
    )
  }
}

export default Issues;
