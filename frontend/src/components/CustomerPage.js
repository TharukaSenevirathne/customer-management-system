import { useState } from "react";
import CustomerForm from "./CustomerForm";
import CustomerList from "./CustomerList";
import api from "../api/axios";

function CustomerPage() {
  const [reload, setReload] = useState(false);
  const [editingCustomer, setEditingCustomer] = useState(null);

  const [file, setFile] = useState(null);
  const [uploadResult, setUploadResult] = useState(null);

  const refresh = () => setReload(!reload);

  const uploadExcel = async () => {
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    const res = await api.post("/customers/upload", formData);
    setUploadResult(res.data);
    refresh();
  };

  return (
    <>
      <CustomerForm
        onCustomerAdded={refresh}
        editingCustomer={editingCustomer}
        clearEdit={() => setEditingCustomer(null)}
      />

      {/* 🔹 Excel Upload */}
      <div className="card">
        <h2>Upload Customers (Excel)</h2>
        <input type="file" onChange={(e) => setFile(e.target.files[0])} />
        <br /><br />
        <button onClick={uploadExcel}>Upload Excel</button>

        {uploadResult && (
          <>
            <p className="success">
              Saved: {uploadResult.saved} | Skipped: {uploadResult.skipped}
            </p>
            {uploadResult.errors.length > 0 && (
              <ul>
                {uploadResult.errors.map((e, i) => (
                  <li key={i}>{e}</li>
                ))}
              </ul>
            )}
          </>
        )}
      </div>

      <CustomerList
        reload={reload}
        onEditCustomer={setEditingCustomer}
      />
    </>
  );
}

export default CustomerPage;
