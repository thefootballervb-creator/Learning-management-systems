import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faChalkboardUser } from "@fortawesome/free-solid-svg-icons";
import { authService } from "../../api/auth.service";

function Navbar(props) {
  const value = props.page;
  const navigate = useNavigate();
  const isAuthenticated = authService.isUserAuthenticated();
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  const handleLogOut = async () => {
    await authService.logout();
    navigate("/login");
  };

  const toggleMobileMenu = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
  };

  const closeMobileMenu = () => {
    setIsMobileMenuOpen(false);
  };

  return (
    <div>
      <nav className="bg-white w-full flex flex-row justify-between items-center px-[4vw] shadow-[2px_2px_10px_rgba(0,0,0,0.15)] z-[999]">
        <div className="flex items-center justify-center">
          <Link to="/" className="flex items-center space-x-2 cursor-pointer">
            <span className="text-2xl font-bold text-sky-500 hover:text-sky-600 transition-colors">
              LEARN HUB
            </span>
          </Link>
        </div>
        <div className="flex">
          <div id="menu-btn" className="hidden">
            <div className="menu-dash" onClick={toggleMobileMenu}>
              &#9776;
            </div>
          </div>
          <i
            id="menu-close"
            className="fas fa-times hidden"
            onClick={closeMobileMenu}
          ></i>
          <ul className={`flex justify-end items-center ${isMobileMenuOpen ? "active" : ""}`}>
            {isMobileMenuOpen && (
              <li className="close-button">
                <button onClick={closeMobileMenu}>X</button>
              </li>
            )}
            {value === "home" ? (
              <li className="list-none ml-5 rounded-[5px] bg-gradient-to-r from-sky-500 to-sky-400">
                <Link 
                  to={"/"} 
                  className="no-underline text-white text-[17px] font-bold transition-all duration-300 ease-in-out px-[10px] py-[2px] block hover:text-sky-100"
                >
                  Home
                </Link>
              </li>
            ) : (
              <li className="list-none ml-5">
                <Link 
                  to={"/"}
                  className="no-underline text-[rgb(21,21,100)] text-[17px] font-bold transition-all duration-300 ease-in-out hover:text-yellow-400"
                >
                  Home
                </Link>
              </li>
            )}
            {value === "courses" ? (
              <li className="list-none ml-5 rounded-[5px] bg-gradient-to-r from-sky-500 to-sky-400">
                <Link
                  to={"/courses"}
                  className="no-underline text-white text-[17px] font-bold transition-all duration-300 ease-in-out px-[10px] py-[2px] block hover:text-sky-100"
                >
                  Courses
                </Link>
              </li>
            ) : (
              <li className="list-none ml-5">
                <Link 
                  to={"/courses"}
                  className="no-underline text-[rgb(21,21,100)] text-[17px] font-bold transition-all duration-300 ease-in-out hover:text-yellow-400"
                >
                  Courses
                </Link>
              </li>
            )}
            {isAuthenticated  ? (
              value === "profile" ? (
                <li className="list-none ml-5 rounded-[5px] bg-gradient-to-r from-sky-500 to-sky-400">
                  <Link
                    to={"/profile"}
                    className="no-underline text-white text-[17px] font-bold transition-all duration-300 ease-in-out px-[10px] py-[2px] block hover:text-sky-100"
                  >
                    Profile
                    <FontAwesomeIcon icon={faUser} className="ml-1" />
                  </Link>
                </li>
              ) : (
                <li className="list-none ml-5">
                  <Link 
                    to={"/profile"}
                    className="no-underline text-[rgb(21,21,100)] text-[17px] font-bold transition-all duration-300 ease-in-out hover:text-yellow-400"
                  >
                    Profile
                    <FontAwesomeIcon icon={faUser} className="ml-1" />
                  </Link>
                </li>
              )
            ) : (
              <></>
            )}
            {isAuthenticated ? (
              value === "learnings" ? (
                <li className="list-none ml-5 rounded-[5px] bg-gradient-to-r from-sky-500 to-sky-400">
                  <Link
                    to={"/learnings"}
                    className="no-underline text-white text-[17px] font-bold transition-all duration-300 ease-in-out px-[10px] py-[2px] block hover:text-sky-100"
                  >
                    Learnings
                    <FontAwesomeIcon icon={faChalkboardUser} className="ml-1" />
                  </Link>
                </li>
              ) : (
                <li className="list-none ml-5">
                  <Link 
                    to={"/learnings"}
                    className="no-underline text-[rgb(21,21,100)] text-[17px] font-bold transition-all duration-300 ease-in-out hover:text-yellow-400"
                  >
                    Learnings
                    <FontAwesomeIcon icon={faChalkboardUser} className="ml-1" />
                  </Link>
                </li>
              )
            ) : (
              <></>
            )}
            {isAuthenticated ? (
              <li className="list-none ml-5">
                <button 
                  onClick={handleLogOut} 
                  className="w-[120px] h-[35px] p-[1px] mb-[1px] bg-sky-500 border-none rounded-lg text-white text-[15px] font-medium cursor-pointer transition-all duration-300 ease-in-out hover:bg-sky-600"
                >
                  Sign Out
                </button>
              </li>
            ) : (
              <li className="list-none ml-5">
                <button 
                  onClick={() => navigate("/login")}
                  className="w-[120px] h-[35px] p-[1px] mb-[1px] bg-sky-500 border-none rounded-lg text-white text-[15px] font-medium cursor-pointer transition-all duration-300 ease-in-out hover:bg-sky-600"
                >
                  Login/SignUp
                </button>
              </li>
            )}
          </ul>
        </div>
      </nav>
    </div>
  );
}

export default Navbar;