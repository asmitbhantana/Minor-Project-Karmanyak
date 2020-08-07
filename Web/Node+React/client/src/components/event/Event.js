import PropTypes from "prop-types";
import React from "react";
import EventItem from "./EventItem";

Event = props => {
  console.log(props);
  return <EventItem events={props.e} onDeleteClick={props.onDeleteClick} onEditClick={props.onEditClick} />;
};
export default Event;

Event.propTypes = {
  name: PropTypes.string.isRequired
};
