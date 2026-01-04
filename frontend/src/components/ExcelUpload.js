import { useState } from "react";
import api from "../api/axios";

function ExcelUpload({ onUploadSuccess }) {
  const [file, setFile] = useState(null);
  const [result, setResult] = useState(null);
  const [error, setError] = useState("");

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setResult(null);
    setError("");
  };

  const handleUpload = async () => {
    if (!file) {
      setError("Please select an Excel file");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
const response = await api.post(
  "/customers/upload",
  formData,
  {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  }
);


      setResult(response.data);
      setError("");

      // refresh customer list
      if (onUploadSuccess) {
        onUploadSuccess();
      }
    } catch (err) {
      setError("Excel upload failed");
    }
  };

  return (
    <div style={{ marginBottom: "30px" }}>
      <h3>Upload Customers (Excel)</h3>

      <input type="file" accept=".xlsx,.xls" onChange={handleFileChange} />
      <br /><br />

      <button onClick={handleUpload}>Upload Excel</button>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {result && (
        <div style={{ marginTop: "15px" }}>
          <p style={{ color: "green" }}>
            Saved: {result.saved} | Skipped: {result.skipped}
          </p>

          {result.errors && result.errors.length > 0 && (
            <>
              <p style={{ color: "red" }}>Errors:</p>
              <ul>
                {result.errors.map((err, index) => (
                  <li key={index}>{err}</li>
                ))}
              </ul>
            </>
          )}
        </div>
      )}
    </div>
  );
}

export default ExcelUpload;
