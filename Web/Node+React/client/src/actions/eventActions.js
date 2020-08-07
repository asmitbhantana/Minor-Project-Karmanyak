import axios from "axios";

export const addEvent = event => dispatch => {
  axios
    .post("api/event/addevent", event)
    // .then(event => {
    //     // Save to localStorage

    //     // // Set token to localStorage
    //     // const { token } = res.data;
    //     // localStorage.setItem("jwtToken", token);
    //     // // Set token to Auth header
    //     // setAuthToken(token);
    //     // // Decode token to get user data
    //     // const decoded = jwt_decode(token);
    //     // // Set current user
    //     // dispatch(setCurrentUser(decoded));

    // })
    .catch(err => console.log("errors" + err));
};
