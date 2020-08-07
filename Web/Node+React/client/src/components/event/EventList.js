import React from "react";
import Grid from "@material-ui/core/Grid";
import axios from "axios";

// import the Contact component
import Event from "./Event";


class EventList extends React.Component {
  // console.log(props);
  onDeleteClicked = (id) => {
    axios
      .delete("/api/event/deleteevent/"+id, null,null)
      .then(response => {
        console.log("deleted: "+response.statusText);
        // this.props.history.push("/login");
        this.props.fetchingEvents();
      })
      .catch(err => console.log(err));
  }
  onEditClicked = (id) => {
    // this.props.history.push("/dashboard/addevent");
    // this.context.history.push("/dashboard/addevent");
  }
  
  render (){
    return(
    <Grid item>
      <Grid container justify="center" spacing={0}>
        {this.props.e.map(event => (
          <Event e={event} onDeleteClick={this.onDeleteClicked} onEditClick={this.onEditClicked}/>
        ))}
      </Grid>
    </Grid>
    )
  }
}

export default EventList;
