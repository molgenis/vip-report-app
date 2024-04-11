import { Component, createSignal, For, Match, onMount, Show, Switch } from "solid-js";
import api from "../api/ApiClient.ts";
import { Pagination } from "../components/Pagination.tsx";
import { Page, VcfRecord } from "../api/Api.ts";

const Home: Component = () => {
  const [vcfRecords, setVcfRecords] = createSignal<Page<VcfRecord>>();

  onMount(() => {
    (async () => {
      await onPageChange(0);
    })().catch((err) => console.error(err));
  });

  async function onPageChange(page: number) {
    const vcfRecords = await api.fetchVcfRecords({ page: page });
    setVcfRecords(vcfRecords);
  }

  return (
    <div class="columns mt-1">
      <div class="column">
        <h1 class="title">Variant Interpretation Pipeline (VIP)</h1>
        <h2 class="subtitle is-4">VCF records</h2>
        <Show when={vcfRecords()} keyed>
          {(vcfRecords) => (
            <>
              <div class="columns">
                <div class="column is-half is-offset-one-quarter">
                  <Pagination page={vcfRecords} onPageChange={(page) => void onPageChange(page)} />
                </div>
                <div class="column is-one-quarter">
                  <span style={{ float: "right" }}>{vcfRecords.totalElements} results</span>
                </div>
              </div>
              <div class="columns" style={{ display: "grid" }}>
                {/* workaround for https://github.com/jgthms/bulma/issues/2572#issuecomment-523099776 */}
                <div class="table-container">
                  <table class="table is-fullwidth">
                    <thead>
                      <tr>
                        <th>Variant</th>
                        {/* hardcoded 5 samples */}
                        <th colSpan={5}>Genotypes</th>
                        {/* 1 + 5 */}
                        <th colSpan={6}>Effects</th>
                      </tr>
                      <tr>
                        <th colSpan={1} />
                        <th>#1</th>
                        <th>#2</th>
                        <th>#3</th>
                        <th>#4</th>
                        <th>#5</th>
                        <th>Gene</th>
                        <th>Consequence</th>
                        <th>HGVS</th>
                        <th>CAPICE</th>
                      </tr>
                    </thead>
                    <tbody>
                      <For each={vcfRecords.content}>
                        {(vcfRecord) => (
                          <tr>
                            <td style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;max-width: 250px;">
                              {vcfRecord.chr}:g.{vcfRecord.pos}
                              {vcfRecord.ref}
                              {">"}
                              {vcfRecord.alt.join(",")}
                            </td>
                            <For each={vcfRecord.genotypes}>
                              {(genotype) => (
                                <td>
                                  <Switch>
                                    <Match
                                      when={
                                        genotype.alleles[0] === vcfRecord.ref && genotype.alleles[1] === vcfRecord.ref
                                      }
                                    >
                                      <div style="display:flex">
                                        <span style="height:24px;width:24px;background-image: -webkit-linear-gradient(-45deg, hsl(141, 71%, 48%) 50%, hsl(141, 71%, 48%) 50%);align-self:center"></span>
                                      </div>
                                    </Match>
                                    <Match
                                      when={
                                        genotype.alleles[0] === vcfRecord.ref &&
                                        genotype.alleles[1] === vcfRecord.alt[0]
                                      }
                                    >
                                      <div style="display:flex">
                                        <span style="height:24px;width:24px;background-image: -webkit-linear-gradient(-45deg, hsl(141, 71%, 48%) 50%, hsl(48, 100%, 67%) 50%);self-align:center"></span>
                                      </div>
                                    </Match>
                                    <Match
                                      when={
                                        genotype.alleles[0] === vcfRecord.alt[0] &&
                                        genotype.alleles[1] === vcfRecord.ref
                                      }
                                    >
                                      <div style="display:flex">
                                        <span style="height:24px;width:24px;background-image: -webkit-linear-gradient(-45deg, hsl(48, 100%, 67%) 50%, hsl(141, 71%, 48%) 50%);self-align:center"></span>
                                      </div>
                                    </Match>
                                    <Match
                                      when={
                                        genotype.alleles[0] === vcfRecord.alt[0] &&
                                        genotype.alleles[1] === vcfRecord.alt[0]
                                      }
                                    >
                                      <div style="display:flex">
                                        <span style="height:24px;width:24px;background-image: -webkit-linear-gradient(-45deg, hsl(48, 100%, 67%) 50%, hsl(48, 100%, 67%) 50%);self-align:center"></span>
                                      </div>
                                    </Match>
                                  </Switch>
                                </td>
                              )}
                            </For>
                            <td>
                              <For each={vcfRecord.effects}>{(effect) => <div>{effect.gene?.symbol || ""}</div>}</For>
                            </td>
                            <td>
                              <For each={vcfRecord.effects}>
                                {(effect) => <div>{effect.consequences.join(", ")}</div>}
                              </For>
                            </td>
                            <td>
                              <For each={vcfRecord.effects}>{(effect) => <div>{effect.hgvs}</div>}</For>
                            </td>
                            <td>
                              <For each={vcfRecord.effects}>
                                {(effect) => (
                                  <Show when={effect.capiceScore} fallback={<br />} keyed>
                                    {(capiceScore) => <div>{capiceScore}</div>}
                                  </Show>
                                )}
                              </For>
                            </td>
                          </tr>
                        )}
                      </For>
                    </tbody>
                  </table>
                </div>
              </div>
            </>
          )}
        </Show>
      </div>
    </div>
  );
};

export default Home;
