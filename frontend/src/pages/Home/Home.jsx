import { useEffect, useState } from "react";
import { getUser } from "../../utils/userService";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

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
    <>
      <div>Name : {user.name}</div>
      <div> Email : {user.email}</div>
      <div>
        {" "}
        Role : {user.role.roleName}
        <button onClick={handleLogout}>Logout</button>
      </div>
    </>
  );
};

export default Home;
