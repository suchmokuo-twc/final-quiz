import { OrderResponseModel } from "../models";
import { httpDelete, httpGet, httpPost, URL } from "./base";

const ENDPOINT = URL + "/orders";

export async function apiCreateOrder(orderRequest) {
  const response = await httpPost(ENDPOINT, orderRequest);

  if (response.status !== 201) {
    const data = await response.json();
    throw new Error(data.error);
  }

  return;
}

export async function apiGetOrders() {
  const response = await httpGet(ENDPOINT);
  const data = await response.json();

  if (response.status !== 200) {
    throw new Error(data.error);
  }

  return data.map(
    ({ id, amount, product }) => new OrderResponseModel(id, amount, product)
  );
}

export async function apiDeleteOrder(id) {
  const response = await httpDelete(`${ENDPOINT}/${id}`);

  if (response.status !== 204) {
    const data = await response.json();
    throw new Error(data.error);
  }

  return;
}
