const ENDPOINT = "http://localhost:8080/products";

export async function apiGetProducts() {
  const response = await fetch(ENDPOINT);
  const data = await response.json();

  if (response.status !== 200) {
    throw new Error(data.error);
  }

  return data;
}
