export type Query = { page: number };
export type Page<T> = {
  content: T[];
  totalPages: number;
  totalElements: number;
  last: boolean;
  size: number;
  number: number;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
};
export type Chromosome = string;
export type Position = number;
export type Allele = string;
export type Consequence = string;
export type Gene = { ncbiGeneId: number; symbol: string };
export type Effect = {
  allele: string;
  consequences: Consequence[];
  gene: Gene;
  capiceScore?: number;
  hgvs?: string;
};
export type Genotype = { alleles: string[] };
export type VcfRecord = {
  chr: Chromosome;
  pos: Position;
  ref: Allele;
  alt: Allele[];
  effects: Effect[];
  genotypes: Genotype[];
};

export interface Api {
  fetchVcfRecords(query?: Query): Promise<Page<VcfRecord>>;
}
