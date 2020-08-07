import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { logoutUser } from "../../actions/authActions";
import axios from "axios";
import Fab from "@material-ui/core/Fab";
// import IconButton from "@material-ui/core/IconButton";
// import AddIcon from "@material-ui/icons/Add";
//import Event from "../event/Event"
import EventList from "../event/EventList";
import { Link } from "react-router-dom";

// let loading = true;
let events = [
  {
    _id: "5cd5bfff951a8b3aa0c44034",
    eventName: "wer",
    eventDesc: "123456",
    reward: 10,
    eventLocation: '"12.3,34.4"',
    eventManager: "Me",
    requiredVolunteer: 12,
    eventImageSrc: "2019-02-18",
    eventDate: "+123455-12-31T18:15:00.000Z",
    __v: 0
  },
  {
    _id: "5cd5bfff951a8b3aa0c44034",
    eventName: "wer",
    eventDesc: "123456",
    reward: 10,
    eventLocation: '"12.3,34.4"',
    eventManager: "Me",
    requiredVolunteer: 12,
    eventImageSrc: "2019-02-18",
    eventDate: "+123455-12-31T18:15:00.000Z",
    __v: 0
  },
  {
    _id: "5cd5bfff951a8b3aa0c44034",
    eventName: "wer",
    eventDesc: "123456",
    reward: 10,
    eventLocation: '"12.3,34.4"',
    eventManager: "Me",
    requiredVolunteer: 12,
    eventImageSrc: "2019-02-18",
    eventDate: "+123455-12-31T18:15:00.000Z",
    __v: 0
  },
  {
    _id: "5cd5bfff951a8b3aa0c44034",
    eventName: "wer",
    eventDesc: "123456",
    reward: 10,
    eventLocation: '"12.3,34.4"',
    eventManager: "Me",
    requiredVolunteer: 12,
    eventImageSrc: "2019-02-18",
    eventDate: "+123455-12-31T18:15:00.000Z",
    __v: 0
  },
  {
    _id: "5cd5bfff951a8b3aa0c44034",
    eventName: "wer",
    eventDesc: "123456",
    reward: 10,
    eventLocation: '"12.3,34.4"',
    eventManager: "Me",
    requiredVolunteer: 12,
    eventImageSrc: "2019-02-18",
    eventDate: "+123455-12-31T18:15:00.000Z",
    __v: 0
  }
];
class Dashboard extends Component {
  constructor() {
    super();
    this.state = {
      loading: true
    };
  }

  state = {
    loading: true
  };

  onLogoutClick = e => {
    e.preventDefault();
    this.props.logoutUser();
  };

  fetchEvents=()=>{
    this.setState({ loading: true });
    axios
      .get("/api/event/getevent")
      .then(response => response.data)
      .then(response => this.setState({ response, loading: false, events }));
  }

  componentDidMount() {
    // fetch('http://localhost:5000/api/crm/getevent')
    // .then(data => data.json())
    // .then(data => this.setState({data, loading: false}))
    // console.log(this.state.data)
    this.fetchEvents();
  }

  render() {
    const { user } = this.props.auth;

    return (
      <div>
        <div class="fixed-action-btn">
          <Fab
            color="secondary"
            aria-label="add"
            style={{
              margin: "20px"
            }}
          >
            <Link to="/dashboard/addevent">       
              <i class="material-icons">add</i>
            </Link>
          </Fab>
          <Fab
            color="secondary"
            aria-label="add"
            onClick={this.onLogoutClick}
            style={{
              margin: "20px"
            }}
          >
            <i class="material-icons">logout</i>
          </Fab>
        </div>
        {this.state.loading ? (
          <div />
        ) : (
          <div className="container valign-wrapper">
            <div className="row">
              <EventList e={this.state.response} fetchingEvents={this.fetchEvents}/>
            </div>
          </div>
        )}
      </div>
    );
  }
}

Dashboard.propTypes = {
  logoutUser: PropTypes.func.isRequired,
  auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  auth: state.auth
});

export default connect(
  mapStateToProps,
  { logoutUser }
)(Dashboard);
