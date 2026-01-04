import { useEffect, useState } from "react";
import api from "../api/axios";

function CustomerForm({ onCustomerAdded, editingCustomer, clearEdit }) {
  const [form, setForm] = useState({
    name: "",
    nic: "",
    dateOfBirth: "",
  });

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    if (editingCustomer) {
      setForm({
        name: editingCustomer.name,
        nic: editingCustomer.nic,
        dateOfBirth: editingCustomer.dateOfBirth,
      });
    }
  }, [editingCustomer]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");

    if (!form.name || !form.nic || !form.dateOfBirth) {
      setError("All fields are required");
      return;
    }

    try {
      if (editingCustomer) {
        await api.put(`/customers/${editingCustomer.id}`, {
          name: form.name,
          dateOfBirth: form.dateOfBirth,
        });

        setMessage("Customer updated successfully");
        clearEdit();
      } else {
        await api.post("/customers", form);
        setMessage("Customer saved successfully");
      }

      setForm({ name: "", nic: "", dateOfBirth: "" });
      onCustomerAdded();
    } catch (err) {
      if (err.response?.status === 409) {
        setError("NIC already exists");
      } else {
        setError("Invalid data");
      }
    }
  };

  return (
    <div className="card">
      <h2>{editingCustomer ? "Edit Customer" : "Create Customer"}</h2>

      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}

      <form onSubmit={handleSubmit}>
        <label>Name</label>
        <input name="name" value={form.name} onChange={handleChange} />

        <label>NIC</label>
        <input
          name="nic"
          value={form.nic}
          disabled={!!editingCustomer}
          onChange={handleChange}
        />

        <label>Date of Birth</label>
        <input
          type="date"
          name="dateOfBirth"
          value={form.dateOfBirth}
          onChange={handleChange}
        />

        <button type="submit">
          {editingCustomer ? "Update Customer" : "Save Customer"}
        </button>

        {editingCustomer && (
          <button
            type="button"
            onClick={clearEdit}
            style={{ marginLeft: "10px" }}
          >
            Cancel
          </button>
        )}
      </form>
    </div>
  );
}

export default CustomerForm;
