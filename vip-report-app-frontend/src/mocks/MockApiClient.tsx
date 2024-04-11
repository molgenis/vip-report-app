import { Api, Page, Query, VcfRecord } from "../api/Api.ts";
import axios from "axios";

export class MockApiClient implements Api {
  async fetchVcfRecords(query: Query): Promise<Page<VcfRecord>> {
    const { data } = await axios.get<Page<VcfRecord>>(`http://localhost:8080/api/vcf?p=${query.page}`, {
      headers: {
        Accept: "application/json",
      },
    });
    return data;
  }
}
