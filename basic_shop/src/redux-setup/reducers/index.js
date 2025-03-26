import { combineReducers } from "redux";
import cartReducer from "./cart"; 

const rootReducer = combineReducers({
  cart: cartReducer, // Đặt tên đúng với Redux store
});

export default rootReducer;
