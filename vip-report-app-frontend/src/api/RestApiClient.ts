import axios from "axios";
import { Api, Page, Query, VcfRecord } from "./Api.ts";

export class RestApiClient implements Api {
  async fetchVcfRecords(query: Query): Promise<Page<VcfRecord>> {
    const { data } = await axios.get<Page<VcfRecord>>(`/api/vcf?p=${query.page}`, {
      headers: {
        Accept: "application/json",
      },
    });
    return data;
  }
}
