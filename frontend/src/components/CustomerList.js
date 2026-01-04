import { useEffect, useState } from "react";
import api from "../api/axios";

function CustomerList({ reload, onEditCustomer }) {
  const [customers, setCustomers] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [sortField, setSortField] = useState("name");
  const [sortDir, setSortDir] = useState("asc");

  useEffect(() => {
    loadCustomers();
  }, [page, sortField, sortDir, reload]);

  const loadCustomers = async () => {
    const res = await api.get(
      `/customers?page=${page}&size=10&sortBy=${sortField}&direction=${sortDir}`
    );

    setCustomers(res.data.content);
    setTotalPages(res.data.totalPages);
  };

  const toggleSort = (field) => {
    if (sortField === field) {
      setSortDir(sortDir === "asc" ? "desc" : "asc");
    } else {
      setSortField(field);
      setSortDir("asc");
    }
  };

  return (
    <div className="card">
      <h2>Customer List</h2>

      <table width="100%" cellPadding="10">
        <thead>
          <tr>
            <th onClick={() => toggleSort("name")} style={{ cursor: "pointer" }}>
              Name {sortField === "name" ? (sortDir === "asc" ? "⬆" : "⬇") : ""}
            </th>
            <th>NIC</th>
            <th
              onClick={() => toggleSort("dateOfBirth")}
              style={{ cursor: "pointer" }}
            >
              Date of Birth{" "}
              {sortField === "dateOfBirth"
                ? sortDir === "asc"
                  ? "⬆"
                  : "⬇"
                : ""}
            </th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {customers.length === 0 && (
            <tr>
              <td colSpan="4" align="center">
                No customers found
              </td>
            </tr>
          )}

          {customers.map((c) => (
            <tr key={c.id}>
              <td>{c.name}</td>
              <td>{c.nic}</td>
              <td>{c.dateOfBirth}</td>
              <td>
                <button onClick={() => onEditCustomer(c)}>
                  Edit
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div style={{ marginTop: "15px", textAlign: "center" }}>
        <button disabled={page === 0} onClick={() => setPage(page - 1)}>
          Previous
        </button>

        <span style={{ margin: "0 15px" }}>
          Page {page + 1} of {totalPages}
        </span>

        <button
          disabled={page + 1 >= totalPages}
          onClick={() => setPage(page + 1)}
        >
          Next
        </button>
      </div>
    </div>
  );
}

export default CustomerList;
