// 5d443e8d24d7d77c3b6749b5
import React, { Component } from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import axios from "axios";
const styles = {
  card: {
    width: 250,
    margin: 15
  },
  media: {
    // ⚠️ object-fit is not supported by IE 11.
    objectFit: "cover"
    // height: 0,
    // paddingTop: '56.25%',
  }
};
class EventItem extends Component {

  onDeleteClicked = id => {
    console.log(id)
    this.props.onDeleteClick(id)
  };

  onEditClicked = id => {
    console.log(id)
    this.props.onEditClick(id)
  };

  render() {
    const { classes } = this.props;
    const event = this.props.events;
    return (
      <Card className={classes.card}>
        <CardActionArea>
          <CardMedia
            component="img"
            alt={`${event.eventName}`}
            className={classes.media}
            height="200"
            image={`${event.eventImageSrc}`}
            title={`${event.eventName}`}
          />
          <CardContent>
            <Typography gutterBottom variant="h5" component="h2">
              {event.eventName}
            </Typography>
            <Typography component="p">{event.eventDesc}</Typography>
          </CardContent>
        </CardActionArea>
        <CardActions>
          <Button 
            size="small" 
            color="primary"             
            onClick={this.onEditClicked.bind(this,event._id)}
          >
            Edit
          </Button>
          <Button
            size="small"
            color="primary"
            onClick={this.onDeleteClicked.bind(this,event._id)}
          >
            Delete
          </Button>
        </CardActions>
      </Card>
    );
  }
}

EventItem.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(EventItem);
