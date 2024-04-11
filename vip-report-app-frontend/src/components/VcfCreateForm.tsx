import { Component, Show } from "solid-js";
import { createStore } from "solid-js/store";
import { Vcf, VcfCreate, VcfUpload } from "../api/Api.ts";
import { useStore } from "../store/store.tsx";
import { FileInput } from "./FileInput.tsx";
import { VcfInput } from "./VcfInput.tsx";
import { Field, FieldLabel } from "./bulma/form/field.tsx";
import { Control } from "./bulma/form/control.tsx";

export type VcfFormData = {
  file: File | null;
  variants: string;
  vcf: Vcf | null;
  isPublic: boolean;
};
export type VcfUploadEvent = {
  vcf: VcfUpload;
};
export type VcfCreateEvent = {
  variants: VcfCreate;
};
export type VcfSelectEvent = {
  vcf: Vcf;
  isPublic: boolean;
};

export const VcfCreateForm: Component<{
  onSubmitVcfUpload: (event: VcfUploadEvent) => void;
  onSubmitVcfCreate: (event: VcfCreateEvent) => void;
  onSubmitVcfSelect: (event: VcfSelectEvent) => void;
  onCancel: () => void;
}> = (props) => {
  const [fields, setFields] = createStore<VcfFormData>({ file: null, variants: "", vcf: null, isPublic: false });
  const [state] = useStore();

  const handleSubmit = (event: Event) => {
    event.preventDefault();

    if (fields.file === null && fields.variants === "" && fields.vcf === null) {
      window.dispatchEvent(
        new CustomEvent("app_error", {
          detail: "Error: upload a VCF file, supply variants or select an existing VCF",
        }),
      );
      return;
    } else if (
      (fields.file !== null && fields.variants !== "") ||
      (fields.file !== null && fields.vcf !== null) ||
      (fields.variants !== "" && fields.vcf !== null)
    ) {
      window.dispatchEvent(
        new CustomEvent("app_error", {
          detail: "Error: upload a VCF file, supply variants or select an existing VCF. multiple options selected.",
        }),
      );
      return;
    } else if (fields.file !== null) {
      const vcf = {
        file: fields.file,
        isPublic: fields.isPublic,
      };
      props.onSubmitVcfUpload({ vcf });
    } else if (fields.variants !== "") {
      const variants = {
        variants: fields.variants,
        isPublic: fields.isPublic,
      };
      props.onSubmitVcfCreate({ variants });
    } else if (fields.vcf !== null) {
      props.onSubmitVcfSelect({ vcf: fields.vcf, isPublic: fields.isPublic });
    } else {
      throw new Error();
    }
  };

  const handleCancel = (event: Event) => {
    event.preventDefault();
    props.onCancel();
  };

  return (
    <form>
      {/* public */}
      <Show when={state.user && state.user.authorities.includes("ROLE_ADMIN")}>
        <Field horizontal>
          <FieldLabel>
            <label class="label">Public</label>
          </FieldLabel>
          <div class="field-body">
            <Field>
              <Control>
                <input class="mr-1" type="checkbox" onInput={(e) => setFields("isPublic", e.target.checked)} />
              </Control>
              <p class="help">Make input data publicly readable</p>
            </Field>
          </div>
        </Field>
      </Show>

      <fieldset class="box">
        <legend class="label has-text-centered">Data</legend>
        {/* vcf upload */}
        <Field horizontal>
          <FieldLabel>
            <label class="label">Upload VCF file</label>
          </FieldLabel>
          <div class="field-body">
            <Field>
              <Control>
                <FileInput
                  accept={[".vcf", ".vcf.bgz", ".vcf.gz"]}
                  maxSize={50}
                  onInput={(e) => setFields("file", e.file)}
                />
              </Control>
              <p class="help">
                File can be uncompressed or{" "}
                <a href="https://www.htslib.org/doc/bgzip.html" target="_blank" rel="noopener noreferrer nofollow">
                  BGZF
                </a>
                -compressed{" "}
                <a
                  href="https://samtools.github.io/hts-specs/VCFv4.3.pdf"
                  target="_blank"
                  rel="noopener noreferrer nofollow"
                >
                  VCF
                </a>{" "}
                and must contain one or more samples.{" "}
                <a
                  href="https://gatk.broadinstitute.org/hc/en-us/articles/360035531812-GVCF-Genomic-Variant-Call-Format"
                  target="_blank"
                  rel="noopener noreferrer nofollow"
                >
                  GVCF
                </a>{" "}
                is not supported. File uploads are limited to 50MB in size.
              </p>
            </Field>
          </div>
        </Field>
        {/* divider */}
        <div class="divider">OR</div>
        {/* variant textarea */}
        <Field horizontal>
          <FieldLabel>
            <label class="label">Input Variants</label>
          </FieldLabel>
          <div class="field-body">
            <Field>
              <Control>
                <textarea class="textarea" onInput={(e) => setFields("variants", e.target.value)} />
              </Control>
              <p class="help">
                Supply one variant per line in the{" "}
                <a href="https://gnomad.broadinstitute.org/" target="_blank" rel="noopener noreferrer nofollow">
                  gnomAD
                </a>{" "}
                variant notation, e.g. 14-74527358-G-A. GRCh38 genome coordinates are assumed.
              </p>
            </Field>
          </div>
        </Field>
        {/* divider */}
        <div class="divider">OR</div>
        {/* existing VCF */}
        <Field horizontal>
          <FieldLabel>
            <label class="label">Select VCF file</label>
          </FieldLabel>
          <div class="field-body">
            <Field>
              <div class="field is-narrow">
                <VcfInput onSelectVcf={(e) => setFields("vcf", e.vcf)} />
              </div>
              <p class="help">Select a VCF file that was previously uploaded or is publicly available.</p>
            </Field>
          </div>
        </Field>
      </fieldset>
      <Field horizontal>
        <FieldLabel />
        <div class="field-body">
          <Field grouped="right">
            <Control>
              <button class="button is-link is-light" onClick={handleCancel}>
                Cancel
              </button>
            </Control>
            <Control>
              <button class="button is-link" onClick={handleSubmit}>
                Continue
              </button>
            </Control>
          </Field>
        </div>
      </Field>
    </form>
  );
};
