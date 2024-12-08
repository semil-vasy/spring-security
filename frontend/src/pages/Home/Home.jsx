import { useEffect, useState } from "react";
import { getUser } from "../../utils/userService";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import "./Home.css";

const Home = () => {
  const navigate = useNavigate();

  const [user, setUser] = useState({
    email: "",
    id: 0,
    name: "",
    role: {
      roleName: "",
    },
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        let response = await getUser();
        console.log(response);
        setUser(response.data);
      } catch (error) {
        toast.error(`${error.response.data.detail}`, {
          theme: "colored",
          closeButton: false,
        });
      }
    };

    fetchData();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("data");
    navigate("/login");
  };

  return (
    <main className="home-container">
      <div className="card">
        <h2 className="name"> {user.name}</h2>
        <p className="email">{user.email}</p>
        <p className="role"> {user.role.roleName}</p>
        <button onClick={handleLogout}>Logout</button>
      </div>
      <div></div>
    </main>
  );
};

export default Home;
