import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import axios from "axios";
import { Map, InfoWindow, Marker, GoogleApiWrapper } from "google-maps-react";

const styles = theme => ({
  container: {
    display: "flex",

    flexWrap: "wrap"
  },

  textField: {
    marginLeft: theme.spacing.unit,

    marginRight: theme.spacing.unit
  },

  dense: {
    marginTop: 16,
    marginButton: 10
  },

  menu: {
    width: 200
  }
});

class AddEvent extends Component {
  constructor() {
    super();
    this.state = {
      //     name: 'Enter Full Name',
      eventName: "",
      eventDesc: "",
      reward: "",
      eventLocation: "",
      eventManager: "",
      requiredVolunteer: "",
      eventImageSrc: "",
      eventDate: "",
      eventEndDate: "",
      eventType: "",
      mapLat: "27.69922",
      mapLng: "85.297334"
    };
  }
  onSubmitClick = e => {
    const event = {
      eventName: this.state.eventName,
      eventDesc: this.state.eventDesc,
      reward: this.state.reward,
      eventLocation: this.state.eventLocation,
      eventManager: this.state.eventManager,
      requiredVolunteer: this.state.requiredVolunteer,
      eventImageSrc: this.state.eventImageSrc,
      eventDate: this.state.eventDate,
      eventEndDate: this.state.eventEndDate,
      eventType: this.state.eventType
    };
    // this.props.addEvent(event);
    // event.preventDefault();
    axios
      .post("/api/event/addevent", event)
      .then(response => {
        // console.log(response);
        this.props.history.push("/dashboard");
      })
      .catch(err => console.log(err.response.data));
    e.preventDefault();
  };

  onChange = e => {
    console.log("working");
    this.setState({ [e.target.name]: e.target.value });
  };

  mapClicked(mapProps, map, clickEvent) {
    let lat = clickEvent.latLng.lat();
    let lng = clickEvent.latLng.lng();
    this.setState({ mapLat: lat });
    this.setState({ mapLng: lng });
    this.state.eventLocation = lat + "," + lng;
  }

  render() {
    return (
      <div>
        <div class="container">
          <form noValidate autoComplete="off">
            <div class="row">
              <div class="col s10 center">
                <TextField
                  label="Name"
                  id="outlined-static"
                  rowsMax="2"
                  type="text"
                  name="eventName"
                  autoComplete="Name"
                  margin="normal"
                  variant="outlined"
                  style={{
                    margin: "10px",
                    width: "500px"
                  }}
                  onChange={this.onChange}
                />
              </div>
              <div class="row center s9">
                <TextField
                  id="outlined-multiline-static"
                  label="Multiline"
                  multiline
                  label="Description"
                  style={{ width: "250px", margin: 8 }}
                  fullWidth
                  rowsMax="4"
                  type="text"
                  name="eventDesc"
                  margin="normal"
                  variant="outlined"
                  style={{
                    margin: 8
                  }}
                  onChange={this.onChange}
                  InputLabelProps={{
                    shrink: true
                  }}
                />
              </div>
              <div class="row center s12">
                <TextField
                  id="outlined-Manager-input"
                  label="Manager"
                  style={{ width: "250px", margin: 8 }}
                  type="text"
                  onChange={this.onChange}
                  name="eventManager"
                  autoComplete="Manager"
                  margin="normal"
                  variant="outlined"
                />

                <TextField
                  id="outlined-Reward-input"
                  label="Reward"
                  style={{ width: "250px", margin: 8 }}
                  onChange={this.onChange}
                  type="number"
                  //autoComplete="current-password"
                  name="reward"
                  margin="normal"
                  variant="outlined"
                />
              </div>
              <div class="center row s12">
                <TextField
                  id="outlined-Required Volunteer"
                  label="Required Volunteer"
                  // className={classNames(classes.textField)}
                  type="number"
                  name="requiredVolunteer"
                  autoComplete="Required Volunteer"
                  onChange={this.onChange}
                  margin="normal"
                  variant="outlined"
                  style={{ width: "250px", margin: 8 }}
                />
                <TextField
                  label="Images"
                  className={"Images"}
                  name="eventImageSrc"
                  onChange={this.onChange}
                  margin="normal"
                  variant="outlined"
                  style={{ width: "250px", margin: 8 }}
                />
                <TextField
                  id="outlined-Images-input"
                  label="Event Type"
                  name="eventType"
                  type="Text"
                  onChange={this.onChange}
                  margin="normal"
                  variant="outlined"
                  style={{ width: "250px", margin: 8 }}
                />
              </div>

              <div class=" center row s12">
                <TextField
                  id="outlined-Start"
                  label="Start Date"
                  type="date"
                  // className={classes.textField}
                  name="eventDate"
                  onChange={this.onChange}
                  margin="normal"
                  variant="outlined"
                  InputLabelProps={{
                    shrink: true
                  }}
                  style={{ width: "250px", margin: 8 }}
                />

                <TextField
                  id="outlined-End Date"
                  label="End Date"
                  // className={classes.textField}
                  type="date"
                  onChange={this.onChange}
                  name="eventEndDate"
                  margin="normal"
                  variant="outlined"
                  InputLabelProps={{
                    shrink: true
                  }}
                  style={{ width: "250px", margin: 8 }}
                />
              </div>

              <div class="row center">
                <button
                  style={{
                    width: "150px",
                    borderRadius: "3px",
                    letterSpacing: "1.5px",
                    marginTop: "1rem",
                    position: "center"
                  }}
                  onClick={this.onSubmitClick}
                  className="btn btn-large waves-effect waves-light hoverable blue accent-3"
                >
                  Sumbit
                </button>
              </div>
            </div>
          </form>
        </div>
        <div class="s12">
          <Map
            styles={{
              width: "100%",
              height: "20px",
              margin: "10px"
            }}
            initialCenter={{
              lat: 27.69922,
              lng: 85.297334
            }}
            google={this.props.google}
            zoom={14}
            onClick={this.mapClicked.bind(this)}
          >
            <Marker
              title={this.state.eventName}
              name={this.state.eventName}
              position={{ lat: this.state.mapLat, lng: this.state.mapLng }}
            />
          </Map>
        </div>
      </div>
    );
  }
}

// AddEvent.propTypes = {
//   classes: PropTypes.object.isRequired
// };
export default GoogleApiWrapper({
  apiKey: "AIzaSyCyf-UE4BdF0SLDg9DlB1hqcamQSQzFpBs"
})(AddEvent);

//export default withStyles(styles)(AddEvent);
