import React, { Component } from "react";
import { Link } from "react-router-dom";

class Navbar extends Component {
  render() {
    return (
      <div className="navbar-fixed nav-wrapper">
        <nav className="z-depth-0">
          <div className="nav-wrapper">
            <Link
              to="/"
              style={{
                fontFamily: "monospace"
              }}
              className="col s5 brand-logo center white-text"
            >
              <i className="material-icons">group</i>
              KARMANYAK
            </Link>
          </div>
        </nav>
      </div>
    );
  }
}

export default Navbar;
