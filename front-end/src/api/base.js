export const URL = "http://localhost:8080";

export async function post(url, body) {
  return await fetch(url, {
    body: JSON.stringify(body), // must match 'Content-Type' header
    // credentials: "*", // include, same-origin, *omit
    headers: {
      "content-type": "application/json",
    },
    method: "POST", // *GET, POST, PUT, DELETE, etc.
    mode: "cors", // no-cors, cors, *same-origin
  });
}
