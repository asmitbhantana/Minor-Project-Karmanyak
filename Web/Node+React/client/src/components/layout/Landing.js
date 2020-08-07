import React, { Component } from "react";
import Login from "./../auth/Login";
import Register from "./../auth/Register";
import {Link} from "react-router-dom"

class Landing extends Component {
  render() {
    return (
      <div style={{ height: "75vh" }} className="container valign-wrapper">
        <div className="row">
          <div className="col s12 center-align">
            <h4>
              <b>jOIN</b>{" "}
              <span style={{ fontFamily: "monospace" }}>KARMANYAK</span> for communnity welfare
            </h4>
            <p className="flow-text grey-text text-darken-1">
              Create a better society by active participation on Community Projects and Earn Life Coins
            </p>
            <br />
            <Link
              to="/register"
              style={{
                width: "150px",
                borderRadius: "3px",
                letterSpacing: "1.5px"
              }}
              className="btn btn-large waves-effect waves-light hoverable blue accent-3"
            >
              Register
            </Link>
            <Link
              to="/login"
              style={{
                marginLeft: "2rem",
                width: "150px",
                borderRadius: "3px",
                letterSpacing: "1.5px"
              }}
              className="btn btn-large waves-effect white hoverable black-text"
            >
              Log In
            </Link>
          </div>
        </div>
      </div>
    );
  }
}

export default Landing;
