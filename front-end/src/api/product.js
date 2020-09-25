import { ProductModel } from "../models";
import { httpGet, URL } from "./base";

const ENDPOINT = URL + "/products";

export async function apiGetProducts() {
  const response = await httpGet(ENDPOINT);
  const data = await response.json();

  if (response.status !== 200) {
    throw new Error(data.error);
  }

  return data.map(
    ({ id, name, price, unit, image }) =>
      new ProductModel(id, name, price, unit, image)
  );
}
