import { NativeModules } from 'react-native';

type ProxyType = {
  disableProxy(): Promise<void>;
  enableDefaultProxy(): Promise<void>;
  setProxyUrl(urls: string, port: number): Promise<void>;
};

const { Proxy } = NativeModules;

export default Proxy as ProxyType;
