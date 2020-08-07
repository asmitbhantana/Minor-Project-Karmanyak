import React, { Component } from "react";
import { Link } from "react-router-dom";

class Footer extends Component {
  render() {
    return (
      <div className="page-footer">
        <div className="z-depth-0">
          <div className="footer">
            <Link
              to="/"
              style={{
                fontFamily: "sans-serif",
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
              }}
              className="footer-copyright"
            >
              &copy; Copyright Team Karmanyak
            </Link>
          </div>
        </div>
      </div>
    );
  }
}

export default Footer;