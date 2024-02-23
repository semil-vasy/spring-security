import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Login.css";
import { login } from "../../utils/authService";
import { toast } from "react-toastify";

const Login = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [errors, setErrors] = useState({
    email: "",
    password: "",
  });

  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    let valid = true;
    const newErrors = { email: "", password: "" };
    if (!formData.email) {
      newErrors.email = "Email is required";
      valid = false;
    }
    if (!formData.password) {
      newErrors.password = "Password is required";
      valid = false;
    }
    if (valid) {
      setIsLoading(true);
      const user = { ...formData };
      delete user.confirmPassword;
      try {
        const response = await login(user);
        console.log(response);
        localStorage.setItem("data", response.data.token);
        navigate("/");
        setFormData({
          email: "",
          password: "",
        });
        toast.success("Login successful !", {
          theme: "colored",
          closeButton: false,
        });
      } catch (error) {
        toast.error(`${error.response.data.detail}`, {
          theme: "colored",
          closeButton: false,
        });
      } finally {
        setIsLoading(false);
      }
    } else {
      setErrors(newErrors);
    }
  };

  return (
    <div className="login-form-container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            disabled={isLoading}
            required
          />
          {errors.email && <span className="error">{errors.email}</span>}
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            disabled={isLoading}
            required
          />
          {errors.password && <span className="error">{errors.password}</span>}
        </div>
        <button type="submit" disabled={isLoading}>
          Login
        </button>
      </form>
      <div className="signup-link">
        Don&apos;t have an account?{" "}
        <Link to="/signup" disabled={isLoading}>
          Sign up
        </Link>
      </div>
    </div>
  );
};

export default Login;
