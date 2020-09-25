import { post, URL } from "./base";

const ENDPOINT = URL + "/orders";

export async function apiCreateOrder(orderRequest) {
  const response = await post(ENDPOINT, orderRequest);

  if (response.status !== 201) {
    const data = await response.json();
    throw new Error(data.error);
  }

  return;
}
