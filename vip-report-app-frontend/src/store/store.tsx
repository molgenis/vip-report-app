import { Context, createContext, ParentComponent, useContext } from "solid-js";
import { createStore } from "solid-js/store";

export type AppState = Record<string, never>;

export type AppActions = Record<string, never>;

export type AppStore = [state: AppState, actions: AppActions];
const defaultState: AppState = {};

const StoreContext = createContext<AppStore>() as Context<AppStore>;

export const Provider: ParentComponent = (props) => {
  const [state] = createStore(defaultState);

  const actions: AppActions = {};
  const store: AppStore = [state, actions];

  return <StoreContext.Provider value={store}>{props.children}</StoreContext.Provider>;
};

export function useStore() {
  return useContext(StoreContext);
}
